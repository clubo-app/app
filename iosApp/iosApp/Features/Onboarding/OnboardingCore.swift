//
//  OnboardingCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture
import SwiftUI

typealias OnboardingStore = StoreOf<OnboardingCore>
typealias OnboardingViewStore = ViewStoreOf<OnboardingCore>

struct OnboardingCore: ReducerProtocol {
        
    struct State: Equatable {
        enum Route: Equatable, Hashable {
            //case login(LoginCore.State)
            //case signup(SignupCore.State)
            case login
            case signup
            case companySignup
        }

        var route: Route? = nil
        
        var signupState: SignupCore.State? = .init()
        var loginState: LoginCore.State? = .init()
        
    }
    
    enum Action: Equatable {
        case signup(SignupCore.Action)
        case login(LoginCore.Action)
        case updateRoute(State.Route?)
        case skipAuth
    }
    
    var body: some ReducerProtocol<State, Action> {
        Reduce { state, action in
            switch action {
            case .signup:
                return .none
            case .login:
                return .none
            case let .updateRoute(route):
                state.route = route
                return .none
            case .skipAuth:
                // handled in parent Store: AppCore
                return .none
            }
        }
        .ifLet(\.signupState, action: /Action.signup) {
            SignupCore()
        }
        .ifLet(\.loginState, action: /Action.login) {
            LoginCore()
        }
    }
}
