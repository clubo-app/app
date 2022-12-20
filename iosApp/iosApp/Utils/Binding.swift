//
//  Binding.swift
//  iosApp
//
//  Created by Jonas Hiltl on 10.12.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

extension Binding where Value: Equatable {
  func removeDuplicates() -> Self {
    .init(
      get: { self.wrappedValue },
      set: { newValue, transaction in
        guard newValue != self.wrappedValue else { return }
        self.transaction(transaction).wrappedValue = newValue
      }
    )
  }
}
