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
    let store: StoreOf<MainNavigationCore>
    @ObservedObject var viewStore: ViewStore<ViewState, MainNavigationCore.Action>
    
    init(store: StoreOf<MainNavigationCore>) {
        self.store = store
        self.viewStore = ViewStore(self.store.scope(state: ViewState.init))
        UITabBar.appearance().isHidden = true
    }
    
    struct ViewState: Equatable {
        let selectedTab: MainNavigationCore.State.Tab
        
        init(state: MainNavigationCore.State) {
            self.selectedTab = state.selectedTab
        }
    }
    
    var body: some View {
        ZStack {
            TabView(
                selection:viewStore.binding(get: \.selectedTab, send: MainNavigationCore.Action.selectTab)
            ) {
                HomeView(
                    store: self.store.scope(state: \.home, action: MainNavigationCore.Action.home)
                )
                    .tag(MainNavigationCore.State.Tab.home)
                
                MapView(
                    store: self.store.scope(state: \.map, action: MainNavigationCore.Action.map)
                )
                .tag(MainNavigationCore.State.Tab.map)
                
                ProfileView(
                    store: self.store.scope(state: \.profile, action: MainNavigationCore.Action.profile)
                )
                .tag(MainNavigationCore.State.Tab.profile)
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
            Button(action: {
                viewStore.send(.selectTab(.home))
            }) {
                VStack(spacing: Padding.s) {
                    Image(systemName: viewStore.selectedTab  == .home ? "star.fill" : "star")
                        .imageScale(.large)
                    Text("Featured")
                        .font(.caption2)
                }
                    .foregroundColor(viewStore.selectedTab == .home ? Color("AccentColor") : .secondary)
                    .frame(maxWidth: .infinity)
                    .contentShape(Rectangle())
            }
            .withScaleEffect(scaledAmount: 0.9)
            
            Button(action: {
                viewStore.send(.selectTab(.map))
            }) {
                VStack(spacing: Padding.s) {
                    Image(systemName: viewStore.selectedTab == .map ? "map.fill" : "map")
                        .imageScale(.large)
                    Text("Map")
                        .font(.caption2)
                }
                    .foregroundColor(viewStore.selectedTab == .map ? Color("AccentColor") : .secondary)
                    .frame(maxWidth: .infinity)
                    .contentShape(Rectangle())
            }
            .withScaleEffect(scaledAmount: 0.9)
            
            Button(action: {
                viewStore.send(.selectTab(.profile))
            }) {
                VStack(spacing: Padding.s) {
                        Image(systemName: viewStore.selectedTab == .profile ? "person.fill" : "person")
                            .imageScale(.large)
                        Text("Profile")
                            .font(.caption2)
                }
                    .foregroundColor(viewStore.selectedTab == .profile ? Color("AccentColor") : .secondary)
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
            store: MainNavigationStore(initialState: .init(), reducer: MainNavigationCore())
        )
    }
}
