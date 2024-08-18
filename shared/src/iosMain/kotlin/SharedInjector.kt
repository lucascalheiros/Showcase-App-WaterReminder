import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.CompleteFirstAccessFlowUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.GetFirstAccessNotificationDataUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsDailyIntakeFirstSetUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.IsFirstAccessCompletedUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstAccessNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstDailyIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetNotificationFrequencyTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetStartNotificationTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetStopNotificationTimeUseCase
import com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.GetHistoryChartDataUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedDrinkUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetDrinkSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetWaterSourceSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetCalculatedIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetThemeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetThemeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileActivityLevelUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileNameUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileTemperatureLevelUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileWeightUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddDrinkInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultVolumeShortcutsUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetTodayWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetWeightUnitUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class SharedInjector: KoinComponent {
    fun getWaterSourceTypeUseCase(): GetWaterSourceTypeUseCase = get()
    fun getWaterSourceUseCase(): GetWaterSourceUseCase = get()
    fun getTodayWaterConsumptionSummaryUseCase(): GetTodayWaterConsumptionSummaryUseCase = get()
    fun getDefaultAddWaterSourceInfoUseCase(): GetDefaultAddWaterSourceInfoUseCase = get()
    fun getDefaultAddDrinkInfoUseCase(): GetDefaultAddDrinkInfoUseCase = get()
    fun getDefaultVolumeShortcutsUseCase(): GetDefaultVolumeShortcutsUseCase = get()
    fun getDailyWaterConsumptionSummaryUseCase(): GetDailyWaterConsumptionSummaryUseCase = get()
    fun saveDailyWaterConsumptionUseCase(): SaveDailyWaterConsumptionUseCase = get()
    fun registerConsumedWaterUseCase(): RegisterConsumedWaterUseCase = get()
    fun createWaterSourceUseCase(): CreateWaterSourceUseCase = get()
    fun createWaterSourceTypeUseCase(): CreateWaterSourceTypeUseCase = get()
    fun deleteWaterSourceTypeUseCase(): DeleteWaterSourceTypeUseCase = get()
    fun deleteWaterSourceUseCase(): DeleteWaterSourceUseCase = get()
    fun deleteConsumedWaterUseCase(): DeleteConsumedWaterUseCase = get()
    fun getConsumedWaterUseCase(): GetConsumedWaterUseCase = get()
    fun getDailyWaterConsumptionUseCase(): GetDailyWaterConsumptionUseCase = get()

    fun getThemeUseCase(): GetThemeUseCase = get()
    fun setThemeUseCase(): SetThemeUseCase = get()
    fun getUserProfileUseCase(): GetUserProfileUseCase = get()
    fun setUserProfileUseCase(): SetUserProfileUseCase = get()
    fun getCalculatedIntakeUseCase(): GetCalculatedIntakeUseCase = get()
    fun setUserProfileNameUseCase(): SetUserProfileNameUseCase = get()
    fun setUserProfileWeightUseCase(): SetUserProfileWeightUseCase = get()
    fun setUserProfileTemperatureLevelUseCase(): SetUserProfileTemperatureLevelUseCase = get()
    fun setUserProfileActivityLevelUseCase(): SetUserProfileActivityLevelUseCase = get()

    fun deleteScheduledNotificationUseCase(): DeleteScheduledNotificationUseCase = get()
    fun getScheduledNotificationsUseCase(): GetScheduledNotificationsUseCase = get()
    fun createScheduleNotificationUseCase(): CreateScheduleNotificationUseCase = get()
    fun setWeekDayNotificationStateUseCase(): SetWeekDayNotificationStateUseCase = get()
    fun isNotificationsEnabledUseCase(): IsNotificationsEnabledUseCase = get()
    fun setNotificationsEnabledUseCase(): SetNotificationsEnabledUseCase = get()

    fun getVolumeUnitUseCase(): GetVolumeUnitUseCase = get()
    fun getTemperatureUnitUseCase(): GetTemperatureUnitUseCase = get()
    fun getWeightUnitUseCase(): GetWeightUnitUseCase = get()
    fun setVolumeUnitUseCase(): SetVolumeUnitUseCase = get()
    fun setTemperatureUnitUseCase(): SetTemperatureUnitUseCase = get()
    fun setWeightUnitUseCase(): SetWeightUnitUseCase = get()

    fun getSortedWaterSourceUseCase(): GetSortedWaterSourceUseCase = get()
    fun getSortedDrinkUseCase(): GetSortedDrinkUseCase = get()
    fun setWaterSourceSortPositionUseCase(): SetWaterSourceSortPositionUseCase = get()
    fun setDrinkSortPositionUseCase(): SetDrinkSortPositionUseCase = get()

    fun getHistoryChartDataUseCase(): GetHistoryChartDataUseCase = get()

    fun completeFirstAccessFlowUseCase(): CompleteFirstAccessFlowUseCase = get()
    fun setFirstDailyIntakeUseCase(): SetFirstDailyIntakeUseCase = get()
    fun isFirstAccessCompletedUseCase(): IsFirstAccessCompletedUseCase = get()
    fun isDailyIntakeFirstSetUseCase(): IsDailyIntakeFirstSetUseCase = get()
    fun getFirstAccessNotificationDataUseCase(): GetFirstAccessNotificationDataUseCase = get()
    fun setFirstAccessNotificationStateUseCase(): SetFirstAccessNotificationStateUseCase = get()
    fun setStartNotificationTimeUseCase(): SetStartNotificationTimeUseCase = get()
    fun setStopNotificationTimeUseCase(): SetStopNotificationTimeUseCase = get()
    fun setNotificationFrequencyTimeUseCase(): SetNotificationFrequencyTimeUseCase = get()
}