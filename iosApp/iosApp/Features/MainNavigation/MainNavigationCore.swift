//
//  MainNavigationCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 21.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture

typealias MainNavigationStore = StoreOf<MainNavigationCore>
typealias MainNavigationViewStore = ViewStoreOf<MainNavigationCore>

struct MainNavigationCore: ReducerProtocol {
    struct State: Equatable {
        enum Tab: Equatable {
            case home
            case map
            case profile
        }
        
        var home = HomeCore.State()
        var map = MapCore.State()
        var profile = ProfileCore.State()
        var selectedTab = Tab.map
    }

    enum Action: Equatable {
        case selectTab(State.Tab)
        case home(HomeCore.Action)
        case map(MapCore.Action)
        case profile(ProfileCore.Action)
    }
    
    var body: some ReducerProtocol<State, Action> {
        Reduce { state, action in
            switch action {
            case let .selectTab(tab):
                state.selectedTab = tab
                return .none
            default:
                return .none
            }
        }
        
        Scope(state: \State.home, action: /Action.home) {
            HomeCore()
        }
        Scope(state: \State.map, action: /Action.map) {
            MapCore()
        }
        Scope(state: \State.profile, action: /Action.profile) {
            ProfileCore()
        }
   }
}
