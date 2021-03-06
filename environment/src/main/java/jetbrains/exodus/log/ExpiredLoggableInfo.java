/**
 * Copyright 2010 - 2018 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.exodus.log;

import jetbrains.exodus.ExodusException;
import org.jetbrains.annotations.NotNull;

public class ExpiredLoggableInfo {

    private static final boolean EXPIRED_LOGGABLE_CHECK_OFF = Boolean.getBoolean("jetbrains.exodus.log.expiredLoggableCheckOff");

    public final long address;
    public final int length;

    public ExpiredLoggableInfo(@NotNull final Loggable loggable) {
        this.address = checkAddressNonNegative(loggable.getAddress());
        this.length = assertPositiveLength(loggable.length());
    }

    public ExpiredLoggableInfo(long address, int length) {
        this.address = checkAddressNonNegative(address);
        this.length = assertPositiveLength(length);
    }

    private static int assertPositiveLength(int length) {
        if (!EXPIRED_LOGGABLE_CHECK_OFF && length < 1) {
            throw new ExodusException("Expired loggable length is negative or nil: " + length);
        }
        return length;
    }

    private static long checkAddressNonNegative(long address) {
        if (!EXPIRED_LOGGABLE_CHECK_OFF && address < 0L) {
            throw new ExodusException("Expired loggable address is negative: " + address);
        }
        return address;
    }
}
