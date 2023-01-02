//
//  iosApp.swift
//  iosApp
//
//  Created by Jonas Hiltl on 14.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import FirebaseCore
import shared

@main
struct ClubbenApp: App {
    //let viewModel: AppViewModel
    
    init() {
        KoinKt.doInitKoin()
        FirebaseApp.configure()
        //viewModel = AppViewModelHelper().vm()
    }
    
    var body: some Scene {
        WindowGroup{
            AppView()
        }
    }
}
