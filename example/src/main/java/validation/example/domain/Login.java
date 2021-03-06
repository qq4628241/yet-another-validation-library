/*
 * Copyright 2010 David Yeung
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package validation.example.domain;

import validation.core.States;
import validation.core.Validator;

public class Login {
    private UserName userName;
    private Password password;

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void describeTo(Validator validator) {
        password.describeTo(validator);
    }

    public void describeTo(States states) {
        userName.describeTo(states);
    }

    public static enum State implements validation.core.State {
        AUTHENTICATED
    }

}
