//
// Copyright 2011 Nectify
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 	http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.nectify.hornet;

class HornetUtils
{
    private final static String baseDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public final static String converter(int base, long decimalNumber)
    { 

        String tempVal = decimalNumber == 0 ? "0" : "";
        long mod = 0;

        while (decimalNumber != 0)
        {
            mod = decimalNumber % base;
            tempVal = baseDigits.substring((int) mod, (int) mod + 1) + tempVal;
            decimalNumber = decimalNumber / base;
        }

        return tempVal;
    }

}
