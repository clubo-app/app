//
//  StoryAvatar.swift
//  iosApp
//
//  Created by Jonas Hiltl on 24.09.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct StoryAvatar: View {
    var width: CGFloat = 65
    var height: CGFloat = 65
        
    var body: some View {
        VStack(spacing: 4) {
            ZStack {
                Image(systemName: "circle.fill")
                    .resizable()
                    .frame(width: width, height: height)
                    .aspectRatio(contentMode: .fit)
                    .clipShape(Circle())
                    .foregroundColor(.gray)
                Circle()
                    .stroke(
                        AngularGradient(
                            gradient: .init(colors: [.blue, .purple, .blue]),
                            center: .center
                        ),
                        style: StrokeStyle(lineWidth: 3, dash: [0])
                    )
                    .frame(width: width+5)
            }
            Text("jonas.hiltl")
                .truncationMode(.tail)
                .lineLimit(1)
                .caption()
        }
            .frame(width: width+5)
    }
}

struct StoryAvatar_Previews: PreviewProvider {
    static var previews: some View {
        StoryAvatar()
    }
}
