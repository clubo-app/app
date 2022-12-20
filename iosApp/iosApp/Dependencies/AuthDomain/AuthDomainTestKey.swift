//
//  TestKey.swift
//  iosApp
//
//  Created by Jonas Hiltl on 29.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import ComposableArchitecture
import XCTestDynamicOverlay

extension AuthDomain: TestDependencyKey {
    static let testValue = Self(
        register: unimplemented("\(Self.self).register"),
        login: unimplemented("\(Self.self).login"),
        consumeAuthState: unimplemented("\(Self.self).consumeAuthState"),
        getCurrentAccount: unimplemented("\(Self.self).getCurrentAccount"),
        signOut: unimplemented("\(Self.self).signOut")
    )
}
