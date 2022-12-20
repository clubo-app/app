//
//  LiveKey.swift
//  iosApp
//
//  Created by Jonas Hiltl on 30.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import ComposableArchitecture
import shared
import KMPNativeCoroutinesAsync

extension ToastQueue: DependencyKey {
    static let liveValue: ToastQueue = ToastQueue.live
}

extension ToastQueue {
    public static var live: Self {
        @LazyKoin var queue: shared.ToastQueue
        let nativeQueue = NativeQueue(
            queue: queue
        )
        return Self(
            consume: nativeQueue.consume
        )
    }
    
    private struct NativeQueue {
        private let queue: shared.ToastQueue
        init(queue: shared.ToastQueue) {
            self.queue = queue
        }
        
        func consume() -> AsyncThrowingStream<shared.Toast?, Error> {
            return asyncStream(for: queue.currentToastNative)
        }
    }
}
