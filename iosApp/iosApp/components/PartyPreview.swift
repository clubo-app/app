//
//  PartyPreview.swift
//  iosApp
//
//  Created by Jonas Hiltl on 23.09.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

var containerWidth: CGFloat = UIScreen.main.bounds.width

struct PartyPreview: View {
    
    var body: some View {
        VStack(alignment: .leading) {
            RoundedRectangle(cornerRadius: CornerRadius.l)
                .fill(Color.secondary)
                .aspectRatio(1, contentMode: .fit)
                .frame(width: containerWidth * 0.6)
                .shadow(radius: 8, y: 8)
                .padding(.bottom, Padding.l)
            
            HStack(alignment: .top) {
                VStack(alignment: .leading, spacing: Padding.s) {
                    Text("Big Birthday Party")
                        .subtitle()
                        .padding(.bottom, Padding.m)
                    HStack {
                        Text("19 März 2023")
                            .caption()
                        Text("2 km")
                            .foregroundColor(Color.green)
                    }
                }
                Spacer()
                HStack(spacing: 4) {
                    Image(systemName: "star.fill")
                        .foregroundColor(Color.yellow)
                    Text("132")
                        .caption()
                }
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
                    .foregroundColor(Color.gray)
            }

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

struct PartyPreview_Previews: PreviewProvider {
    static var previews: some View {
        PartyPreview()
    }
}
