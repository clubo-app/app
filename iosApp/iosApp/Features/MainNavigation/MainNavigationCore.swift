//
//  MainNavigationCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 21.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class MainNavigationViewModel: ObservableObject {
    @Published var destination: Destination = .home
    
    enum Destination{
        case home
        case map
        case profile
    }
    
    func selectTab(tab: Destination) {
        destination = tab
    }
    
    func goToHome() {
        destination = .home
    }
    
    func goToMap() {
        destination = .map
    }
    
    func goToProfile() {
        destination = .profile
    }
}
