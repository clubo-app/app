//
//  OnboardingCore.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class OnboardingViewModel: shared.OnboardingViewModel {
    @Published var destination: Destination?
    
    enum Destination{
        case login(LoginViewModel)
        case signup(SignUpViewModel)
    }
    
    func goToLogin() {
        self.destination = .login(.init())
    }
    
    func goToSignup() {
        self.destination = .signup(.init())
    }
}
