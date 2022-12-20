//
//  MapCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture

typealias MapStore = StoreOf<MapCore>
typealias MapViewStore = ViewStoreOf<MapCore>

struct MapCore: ReducerProtocol {
    struct State: Equatable {
        
    }
    
    enum Action: Equatable {
        
    }
    
    func reduce(into state: inout State, action: Action) -> EffectTask<Action> {
        
    }
}
