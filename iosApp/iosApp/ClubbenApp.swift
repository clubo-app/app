//
//  iosApp.swift
//  iosApp
//
//  Created by Jonas Hiltl on 14.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import FirebaseCore

@main
struct ClubbenApp: App {
    
    init() {
        let _ = KoinApplication.start()
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            AppView(
                store: AppStore(initialState: AppCore.State(), reducer: AppCore())
            )
        }
    }
}
