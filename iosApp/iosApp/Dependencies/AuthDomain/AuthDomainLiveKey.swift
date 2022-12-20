//
//  LiveKey.swift
//  iosApp
//
//  Created by Jonas Hiltl on 29.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import KMPNativeCoroutinesAsync
import Dependencies
import FirebaseCore

extension AuthDomain: DependencyKey {
    static let liveValue: AuthDomain = AuthDomain.live()
}

extension AuthDomain{
    public static func live(domain: shared.AuthDomain = shared.AuthDomain() ) -> Self {
        let impl = NativeRepo(domain: domain)
        
        return Self(
            register: impl.register,
            login: impl.login,
            consumeAuthState: impl.consumeAuthState,
            getCurrentAccount: impl.getCurrentAccount,
            signOut: impl.signOut
        )
    }
    
    // A wrapper around the shared methods so that they conform with
    private struct NativeRepo {
        private let domain: shared.AuthDomain
        
        init(domain: shared.AuthDomain) {
            self.domain = domain
        }
       
        func register(from req: RegisterRequest) async -> DataStateKs<Account, ApiError> {
            do {
                let res = try await asyncFunction(for: domain.registerNative(
                    email: req.email,
                    password: req.password,
                    username: req.username,
                    firstname: req.firstname,
                    lastname: req.lastname,
                    avatar: req.avatar
                ))
                return DataStateKs(res)
            } catch {
                return .failure(.init(error: .init(code: 400, message: "Failed to register")))
            }
        }
        
        func login(from email: String, password: String) async -> DataStateKs<Account, ApiError> {
            do {
                let res = try await asyncFunction(for: domain.loginNative(email: email, password: password))
                return DataStateKs(res)
            } catch {
                return .failure(.init(error: .init(code: 400, message: "Failed to log in")))
            }
        }
       
        func consumeAuthState() -> AsyncThrowingStream<Account?, Error> {
            return asyncStream(for: domain.consumeAuthStateNative())
        }
        
        func getCurrentAccount() async -> Account? {
            do {
                return try await asyncFunction(for: domain.getCurrentAccountNative())
            } catch {
                print("Error: \(error)")
                return nil
            }
        }
        
        func signOut() async -> Void {
            do {
                let _ = try await asyncFunction(for: domain.signOutNative())
            } catch {
                
            }
            return
        }
    }
}
