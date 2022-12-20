//
//  LoginCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 05.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture
import shared

struct LoginCore: ReducerProtocol {
    struct State: Equatable, Hashable {
        var email = ""
        var emailValid = false
        var password = ""
        var showPassword = false
        var passwordValid = false
        var googleToken = ""
        var appleToken = ""
        var loginLoading = false
    }
    
    enum Action: Equatable {
        case emailChanged(String)
        case passwordChanged(String)
        case googleTokenChanged(String)
        case appleTokenChanged(String)
        case togglePassword
        
        case loginButtonPressed
        case loginSuccess
        case loginFailed
    }
    
    @Dependency(\.authDomain) var authDomain
    
    func reduce(into state: inout State, action: Action) -> EffectTask<Action> {
        switch action {
        case let .emailChanged(email):
            state.email = email
            let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
            let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
            state.emailValid =  emailPred.evaluate(with: email)
            return .none
        case let .passwordChanged(password):
            state.password = password
            state.passwordValid = password.count >= 8
            return .none
        case let .googleTokenChanged(token):
            state.googleToken = token
            return .none
        case let .appleTokenChanged(token):
            state.appleToken = token
            return .none
        case .togglePassword:
            state.showPassword.toggle()
            return .none
        case .loginButtonPressed:
            state.loginLoading = true
            return .task { [state] in
                let res = try await authDomain.login(state.email, state.password)
                switch res {
                case .success:
                    return .loginSuccess
                case .failure:
                    return .loginFailed
                }
            }
        case .loginFailed:
            state.loginLoading = false
            return .none
        case .loginSuccess:
            state.loginLoading = false
            return .none
        }
    }
}
