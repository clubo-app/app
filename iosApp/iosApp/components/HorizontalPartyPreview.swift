//
//  HorizontalPartyPreview.swift
//  iosApp
//
//  Created by Jonas Hiltl on 24.09.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct HorizontalPartyPreview: View {
    var body: some View {
        HStack(alignment: .center, spacing: Padding.l) {
            RoundedRectangle(cornerRadius: CornerRadius.l)
                    .fill(Color.secondary)
                    .aspectRatio(1, contentMode: .fit)
                    .frame(width: containerWidth * 0.2)
                    .shadow(radius: 8, y: 8)
            
            VStack(alignment: .leading) {
                    Text("Big Birthday Party")
                        .subtitle()
                    HStack {
                        Text("15 min")
                            .caption()
                        Text("2 km")
                            .foregroundColor(Color.green)
                    }
                    Divider()
                    HStack() {
                        HStack(spacing: -8) {
                            Circle()
                                .foregroundColor(Color.yellow)
                                .frame(width: 24, height: 24)
                            Circle()
                                .foregroundColor(Color.purple)
                                .frame(width: 24, height: 24)
                            Circle()
                                .foregroundColor(Color.blue)
                                .frame(width: 24, height: 24)
                        }
                        Text("+15 Participants")
                            .caption()
                    }
                }
                Spacer()
            }
            .padding(Padding.l)
            .background(
                RoundedRectangle(cornerRadius: CornerRadius.l)
                    .fill(Color(UIColor.secondarySystemBackground))
            )
            .onTapGesture {
                
            }
    }
}

struct HorizontalPartyPreview_Previews: PreviewProvider {
    static var previews: some View {
        HorizontalPartyPreview()
    }
}
