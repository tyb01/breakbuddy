package com.pawsup.billing;

import android.app.Activity;
import android.content.Context;
import com.android.billingclient.api.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.SharedFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0004"}, d2 = {"PRODUCT_QUERY_MAX_ATTEMPTS", "", "PRODUCT_QUERY_RETRY_DELAY_MS", "", "app_debug"})
public final class BillingClientWrapperKt {
    private static final int PRODUCT_QUERY_MAX_ATTEMPTS = 3;
    private static final long PRODUCT_QUERY_RETRY_DELAY_MS = 500L;
}