//
//  ToastQueue.swift
//  iosApp
//
//  Created by Jonas Hiltl on 30.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import ComposableArchitecture

extension DependencyValues {
    var toastQueue: ToastQueue{
        get { self[ToastQueue.self] }
        set { self[ToastQueue.self] = newValue }
    }
}


struct ToastQueue {
    public var consume: () -> AsyncThrowingStream<shared.Toast?, Error>
}
