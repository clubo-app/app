//
//  ToastView.swift
//  iosApp
//
//  Created by Jonas Hiltl on 05.11.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import ComposableArchitecture
import KMPNativeCoroutinesAsync

struct ToastView: View {
    let store: ToastStore
    
    var body: some View {
        WithViewStore(self.store) { viewStore in
            VStack {
                if let currentToast = viewStore.currentToast {
                    HStack {
                        Image(systemName: currentToast.icon)
                        Text(currentToast.message)
                            .lineLimit(2)
                    }
                    .foregroundColor(currentToast.border)
                    .frame(minWidth: 0, maxWidth: .infinity)
                    .padding(Padding.l)
                    .background(currentToast.background)
                    .overlay(
                        Rectangle()
                            .frame(height: 1)
                            .foregroundColor(currentToast.border),
                        alignment: .bottom
                    )
                    .onTapGesture {
                        viewStore.send(.setToast(nil), animation: .default)
                    }
                    .transition(.move(edge: .top))
                    
                    Spacer()
                }
            }
            .task {
                await viewStore.send(.consume, animation: .default).finish()
            }
        }
    }
}

struct ToastView_Previews: PreviewProvider {
    static var previews: some View {
        ToastView(
            store: ToastStore(initialState: .init(), reducer: ToastCore())
        )
    }
}
