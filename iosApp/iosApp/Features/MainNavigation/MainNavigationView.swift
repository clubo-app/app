//
//  MainNavigationView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 21.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import ComposableArchitecture

struct MainNavigationView: View {
    @ObservedObject var viewModel: MainNavigationViewModel
    
    var body: some View {
        ZStack {
            TabView(
                selection: $viewModel.destination
            ) {
                HomeView()
                    .tag(MainNavigationViewModel.Destination.home)
                
                MapView()
                    .tag(MainNavigationViewModel.Destination.map)
                
                ProfileView()
                    .tag(MainNavigationViewModel.Destination.profile)
            }
            VStack(spacing: 0) {
                Spacer()
                Divider()
                tabBar
            }
        }
    }
    
    var tabBar: some View {
        HStack(spacing: 0) {
            Button(action: viewModel.goToHome) {
                VStack(spacing: Padding.s) {
                    Image(systemName: viewModel.destination == .home ? "star.fill" : "star")
                        .imageScale(.large)
                    Text("Featured")
                        .font(.caption2)
                }
                    .foregroundColor(viewModel.destination == .home ? Color("AccentColor") : .secondary)
                    .frame(maxWidth: .infinity)
                    .contentShape(Rectangle())
            }
            .withScaleEffect(scaledAmount: 0.9)
            
            Button(action: viewModel.goToMap) {
                VStack(spacing: Padding.s) {
                    Image(systemName: viewModel.destination == .map ? "map.fill" : "map")
                        .imageScale(.large)
                    Text("Map")
                        .font(.caption2)
                }
                    .foregroundColor(viewModel.destination == .map ? Color("AccentColor") : .secondary)
                    .frame(maxWidth: .infinity)
                    .contentShape(Rectangle())
            }
            .withScaleEffect(scaledAmount: 0.9)
            
            Button(action: viewModel.goToProfile) {
                VStack(spacing: Padding.s) {
                        Image(systemName: viewModel.destination == .profile ? "person.fill" : "person")
                            .imageScale(.large)
                        Text("Profile")
                            .font(.caption2)
                }
                    .foregroundColor(viewModel.destination == .profile ? Color("AccentColor") : .secondary)
                    .frame(maxWidth: .infinity)
                    .contentShape(Rectangle())
            }
            .withScaleEffect(scaledAmount: 0.9)
            
        }
        .frame(maxWidth: .infinity)
        .padding(.top, Padding.m)
    }
    
}

struct MainNavigationView_Previews: PreviewProvider {
    static var previews: some View {
        MainNavigationView(
            viewModel: .init()
        )
    }
}
