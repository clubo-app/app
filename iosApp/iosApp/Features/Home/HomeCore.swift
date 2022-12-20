//
//  HomeCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture

typealias HomeStore = StoreOf<HomeCore>
typealias HomeViewStore = ViewStoreOf<HomeCore>

struct HomeCore: ReducerProtocol {
    struct State: Equatable {
        
    }
    
    enum Action: Equatable {
        
    }
    
    func reduce(into state: inout State, action: Action) -> EffectTask<Action> {
        
    }
}
