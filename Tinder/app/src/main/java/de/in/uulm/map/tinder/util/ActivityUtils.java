
/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.in.uulm.map.tinder.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code
     * frameId}. The operation is performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,
                                             int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment checkFragment = fragmentManager.findFragmentById(frameId);

        if (checkFragment == null) {
            transaction.add(frameId, fragment);
        } else {
            // Use this for animations
            /* transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit, R.anim
                                .fragment_slide_right_enter, R.anim
                                .fragment_slide_right_exit);
                transaction.addToBackStack(null); */

            transaction.replace(frameId, fragment);
        }

        transaction.commit();
    }
}
