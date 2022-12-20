//
//  ApplicationUtility.swift
//  iosApp
//
//  Created by Jonas Hiltl on 12.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

final class ApplicationUtility {
    static var rootViewController: UIViewController {
        guard let screen = UIApplication.shared.connectedScenes.first as? UIWindowScene else {
            return .init()
        }
        
        guard let root = screen.windows.first?.rootViewController else {
            return .init()
        }
        
        return root
    }
}
