//
//  AuthCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 05.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import ComposableArchitecture

enum AuthState {
    case authenticated
    case unauthenticated
    case skipped
}

struct AuthCore: ReducerProtocol {
    struct State: Equatable {
        var state: AuthState = .unauthenticated
    }
}
