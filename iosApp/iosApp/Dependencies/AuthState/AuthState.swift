//
//  AuthState.swift
//  iosApp
//
//  Created by Jonas Hiltl on 29.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Combine
import ComposableArchitecture

struct AuthState {
    var showAuth: () -> EffectTask<Bool?>
}

extension AuthState {
}
