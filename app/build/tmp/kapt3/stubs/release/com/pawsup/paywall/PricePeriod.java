package com.pawsup.paywall;

import android.app.Activity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.billingclient.api.ProductDetails;
import com.pawsup.billing.BillingClientWrapper;
import com.pawsup.billing.BillingRepository;
import com.pawsup.billing.ProductIds;
import com.pawsup.cats.Cat;
import com.pawsup.cats.CatRegistry;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/pawsup/paywall/PricePeriod;", "", "(Ljava/lang/String;I)V", "MONTHLY", "YEARLY", "app_release"})
public enum PricePeriod {
    /*public static final*/ MONTHLY /* = new MONTHLY() */,
    /*public static final*/ YEARLY /* = new YEARLY() */;
    
    PricePeriod() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.pawsup.paywall.PricePeriod> getEntries() {
        return null;
    }
}