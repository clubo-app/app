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
import KMMViewModelSwiftUI

struct ToastView: View {
    @StateViewModel var viewModel: ToastViewModel = ToastViewModelHelper().vm()
    
    var body: some View {
        VStack {
            if let toast = viewModel.currentToast {
                HStack {
                    Image(systemName: toast.icon)
                    Text(toast.message)
                        .lineLimit(2)
                }
                .foregroundColor(toast.border)
                .frame(minWidth: 0, maxWidth: .infinity)
                .padding(Padding.l)
                .background(toast.background)
                .overlay(
                    Rectangle()
                        .frame(height: 1)
                        .foregroundColor(toast.border),
                    alignment: .bottom
                )
                .onTapGesture {
                    viewModel.remove(id: toast.id)
                }
                .transition(.opacity.combined(with: .move(edge: .top)))
                .task {
                    try? await Task.sleep(for: .seconds(3))
                    viewModel.remove(id: toast.id)
                }
            }
            Spacer()
        }
        .zIndex(2)
        .animation(.default, value: viewModel.currentToast)
    }
}

struct ToastView_Previews: PreviewProvider {
    static var previews: some View {
        ToastView(
            viewModel: .init()
        )
    }
}
