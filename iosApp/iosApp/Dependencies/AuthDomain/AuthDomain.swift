//
//  AuthRepositoryInterface.swift
//  iosApp
//
//  Created by Jonas Hiltl on 29.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import ComposableArchitecture

extension DependencyValues {
    var authDomain: AuthDomain {
        get { self[AuthDomain.self] }
        set { self[AuthDomain.self] = newValue }
    }
}

struct AuthDomain: @unchecked Sendable {
    var register: (RegisterRequest) async -> DataStateKs<Account, ApiError>
    var login: (String, String) async -> DataStateKs<Account, ApiError>
    var consumeAuthState: () -> AsyncThrowingStream<Account?, Error>
    var getCurrentAccount: () async -> Account?
    var signOut: () async -> Void
}
