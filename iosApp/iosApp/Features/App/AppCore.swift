//
//  AppCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 06.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import ComposableArchitecture
import KMPNativeCoroutinesAsync

typealias AppStore = StoreOf<AppCore>
typealias AppViewStore = ViewStoreOf<AppCore>

struct AppCore: ReducerProtocol {
    struct State: Equatable {
        var authenticated: MainNavigationCore.State = MainNavigationCore.State()
        var unauthenticated: OnboardingCore.State? = nil
        var toast: ToastCore.State = .init()
        var account: Account?
    }
    
    enum Action: Equatable {
        case authenticated(MainNavigationCore.Action)
        case unauthenticated(OnboardingCore.Action)
        
        case toast(ToastCore.Action)
        
        case showAuthentication
        case dismissAuthentication
        case consumeAuthChanges
        case onInit
        
    }
    
    @Dependency(\.authDomain) var authDomain
    
    var body: some ReducerProtocol<State, Action> {
        Reduce { state, action in
            switch action {
            case .dismissAuthentication:
                state.unauthenticated = nil
                return .none
            case .showAuthentication:
                state.unauthenticated = .init()
                return .none
            case .unauthenticated(.skipAuth):
                state.unauthenticated = nil
                return .none
            case .unauthenticated(.signup(.signupSuccess)):
                state.unauthenticated = nil
                return .none
            case .unauthenticated(.login(.loginSuccess)):
                state.unauthenticated = nil
                return .none
            case .onInit:
                return .run { send in
                    let account = await authDomain.getCurrentAccount()
                    print("Got account \(account)")
                    if (account == nil) {
                        await send(.showAuthentication)
                    }
                }
            default:
                return .none
            }
        }
        .ifLet(\.unauthenticated, action: /Action.unauthenticated) {
            OnboardingCore()
        }

        Scope(state: \.authenticated, action: /Action.authenticated) {
            MainNavigationCore()
        }
  
        Scope(state: \.toast, action: /Action.toast) {
            ToastCore()
        }
   }
}
