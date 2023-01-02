//
//  AppView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 07.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import SwiftUINavigation
import KMMViewModelSwiftUI
import shared
import AlternativeSheet

struct AppView: View {
    @StateViewModel var viewModel: AppViewModel = AppViewModelHelper().vm()
    
    var body: some View {
        ZStack {
            MainNavigationView(viewModel: .init())
            .alternativeSheet(
                isPresented: Binding(get: {viewModel.showAuthFlow}, set: viewModel.setAuthFlow),
                snaps: [0.95]
            ) {
                OnboardingView(onboardingViewModel: .init())
            }
            .isDraggable()
            .dampenDrag()
            
            ToastView()
        }
    }
}

struct AppView_Previews: PreviewProvider {
    static var previews: some View {
        AppView(
            viewModel: .init()
        )
    }
}
