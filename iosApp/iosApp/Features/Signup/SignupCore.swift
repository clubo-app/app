//
//  SignupCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 05.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture
import shared

struct SignupCore: ReducerProtocol {
    struct State: Equatable, Hashable {
        enum Tab {
            case method
            case name
            case password
        }
        
        var currentTab = Tab.method
        var email = ""
        var emailValid = false
        var username = ""
        var usernameTaken = false
        var firstname = ""
        var lastname = ""
        var password = ""
        var passwordValid = false
        var showPassword = false
        
        var signUpLoading = false
    }

    
    enum Action: Equatable {
        case emailChanged(String)
        case usernameChanged(String)
        case firstnameChanged(String)
        case lastnameChanged(String)
        case passwordChanged(String)
        case togglePassword
        
        case tabSelected(State.Tab)
        case tabBack
       
        case signupButtonPressed
        case signupSuccess
        case signupFailed
    }
    
    @Dependency(\.authDomain) var authDomain
    @Dependency(\.mainQueue) var main
    
    func reduce(into state: inout State, action: Action) -> EffectTask<Action> {
        switch action {
        case let .emailChanged(email):
            state.email = email
            let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
            let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
            state.emailValid =  emailPred.evaluate(with: email)
            return .none
        case let .usernameChanged(username):
            state.username = username
            return .none
        case let .firstnameChanged(firstname):
            state.firstname = firstname
            return .none
        case let .lastnameChanged(lastname):
            state.lastname = lastname
            return .none
        case let .passwordChanged(password):
            state.password = password
            state.passwordValid = password.count >= 8
            return .none
        case let .tabSelected(tab):
            state.currentTab = tab
            return .none
        case .togglePassword:
            state.showPassword.toggle()
            return .none
        case .tabBack:
            switch state.currentTab {
            case .method:
                break;
            case .name:
                state.currentTab = .method
            case .password:
                state.currentTab = .name
            }
            return .none
        case .signupButtonPressed:
            state.signUpLoading = true
            return .task { [state] in
                let res = try await authDomain.register(
                    RegisterRequest(
                        email: state.email,
                        password: state.password,
                        username: state.username,
                        firstname: state.firstname,
                        lastname: state.lastname,
                        avatar: nil
                    )
                )
                switch res {
                case .success:
                    return .signupSuccess
                case .failure:
                    return .signupFailed
                }
            }
        case .signupSuccess:
            state.signUpLoading = false
            return .none
        case .signupFailed:
            state.signUpLoading = false
            return .none
        }
    }
}
