//
//  TextFieldStyles.swift
//  iosApp
//
//  Created by Jonas Hiltl on 08.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct TextFieldStyles: View {
    var body: some View {
        VStack(spacing: Padding.l) {
            TextField("Placeholder", text: .constant(""))
                .filledTextField()
            
            TextField("Placeholder", text: .constant("Error State"))
                .filledTextField(isError: true)
            
            HStack {
                Image(systemName: "checkmark")
                TextField("Advanced Usage", text: .constant(""))
            }
            .filledTextField()

        }
        .padding()
    }
}

extension View {
    func filledTextField(isError: Bool = false) -> some View {
        @Environment(\.isEnabled) var isEnabled
        
        return self
            .padding(Padding.l)
            .background(RoundedRectangle(cornerRadius: CornerRadius.s)
                .stroke(isError ? Color("ErrorBorder") : Color("TextFieldBorder"))
                .background(RoundedRectangle(cornerRadius: CornerRadius.s)
                    .fill(isError ? Color("ErrorBG") :  Color("SecondaryBackground"))
                )
            )
    }
}

extension View {
    func emailField() -> some View {
        return self
            .keyboardType(.emailAddress)
            .textContentType(.emailAddress)
            .textInputAutocapitalization(.never)

    }
}

struct TextFieldStyles_Previews: PreviewProvider {
    static var previews: some View {
        TextFieldStyles()
    }
}
