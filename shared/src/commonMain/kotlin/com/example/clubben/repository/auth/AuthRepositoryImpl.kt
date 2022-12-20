package com.example.clubben.repository.auth

import co.touchlab.kermit.Logger
import com.example.clubben.di.ClubbenDatabaseWrapper
import com.example.clubben.remote.auth.AuthApi
import com.example.clubben.remote.auth.RegisterRequest
import com.example.clubben.remote.auth.RemoteAccount
import com.example.clubben.remote.profiles.toDBProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthRepositoryImpl() : KoinComponent, AuthRepository {
    private val authApi: AuthApi by inject()
    private val logger = Logger.withTag("AuthRepositoryImpl")

    private val auth = Firebase.auth
    private val clubbenDatabase: ClubbenDatabaseWrapper by inject()
    private val profileQueries = clubbenDatabase.instance?.profileQueries
    private val accountQueries = clubbenDatabase.instance?.localAccountQueries

    override suspend fun getMe(): RemoteAccount {
        return authApi.me()
    }

    override suspend fun login(
        email: String,
        password: String
    ): RemoteAccount? {
        val res = auth.signInWithEmailAndPassword(email, password)
        if (res.user != null) {
            return RemoteAccount(
                id = res.user!!.uid,
                email = res.user!!.email,
                emailVerified = res.user!!.isEmailVerified,
                providerId = res.user!!.providerId,
            )
        }
        return null
    }

    override suspend fun register(req: RegisterRequest): RemoteAccount {
        val res = authApi.register(req)

        val profile = res.account.profile

        profile?.let {
            profileQueries?.insert(it.toDBProfile())
        }
        accountQueries?.insert(
            res.account.id,
            res.account.email!!,
            res.account.emailVerified,
            res.account.providerId
        )

        logger.i("Got Response $res")
        auth.signInWithCustomToken(res.customToken)

        return res.account
    }

    override suspend fun googleLogin(
        accessToken: String,
        idToken: String?
    ): DataState<RemoteAccount, ApiError> {
        val credential = GoogleAuthProvider.credential(idToken, accessToken)

        if (auth.currentUser == null) {
            val tokenRes = authApi.registerAnon()
            auth.signInWithCustomToken(tokenRes.customToken)
        }

        val res = auth.currentUser?.linkWithCredential(credential)

        val user = res?.user
        return if (user != null) {
            DataState.Success(
                RemoteAccount(
                    id = user.uid,
                    email = user.email,
                    emailVerified = user.isEmailVerified,
                    providerId = user.providerId,
                )
            );
        } else {
            DataState.Failure(
                ApiError(message = "Failed to link your Google Account")
            )
        }
    }

    override suspend fun signOut() = auth.signOut()

    override fun getCurrentAccount(): RemoteAccount? {
        val user = auth.currentUser
        return if (user != null) {
            RemoteAccount(
                id = user.uid,
                email = user.email,
                emailVerified = user.isEmailVerified,
                providerId = user.providerId
            )
        } else {
            null
        }
    }

    override val authStateChanged: Flow<RemoteAccount?>
        get() {
            return flow {
                auth.authStateChanged.collect { user ->
                    if (user != null) {
                        emit(
                            RemoteAccount(
                                id = user.uid,
                                email = user.email,
                                emailVerified = user.isEmailVerified,
                                providerId = user.providerId
                            )
                        );
                    } else {
                        emit(null)
                    }
                }
            }
        }
}