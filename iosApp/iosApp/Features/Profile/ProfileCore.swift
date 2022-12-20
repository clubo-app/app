//
//  ProfileCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture

typealias ProfileStore = StoreOf<ProfileCore>
typealias ProfileViewStore = ViewStoreOf<ProfileCore>

struct ProfileCore: ReducerProtocol {
    struct State: Equatable {
        
    }
    
    enum Action: Equatable {
        case signOut
    }
    
    @Dependency(\.authDomain) var authDomain
    
    func reduce(into state: inout State, action: Action) -> EffectTask<Action> {
        switch action {
        case .signOut:
            return .run { _ in
                try await authDomain.signOut()
            }
        }
    }
}
