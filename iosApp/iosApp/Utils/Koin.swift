//
//  Koin.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared

public typealias KoinApplication = Koin_coreKoinApplication
public typealias Koin = Koin_coreKoin

extension KoinApplication {
    public static let shared = companion.start()

    @discardableResult
    public static func start() -> KoinApplication {
        shared
    }
}

extension KoinApplication {
    private static let sharedKeyPaths: [PartialKeyPath<Koin>] = [
        \.toastQueue
    ]
    
    static func inject<T>() -> T {
        shared.inject()
    }
    
    func inject<T>() -> T {
        for partialKeyPath in Self.sharedKeyPaths {
            guard let keyPath = partialKeyPath as? KeyPath<Koin, T> else { continue }
            return koin[keyPath: keyPath]
        }
        
        fatalError("\(T.self) is not registered with KoinApplication")
    }
}

@propertyWrapper
struct LazyKoin<T> {
    var wrappedValue: T = { KoinApplication.shared.inject() }()
    
    init() {}
}
