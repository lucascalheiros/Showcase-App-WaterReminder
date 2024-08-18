package com.github.lucascalheiros.waterreminder.domain.history.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ChartOption
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.HistoryChartData
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface GetHistoryChartDataUseCase {
    /**
     * @param option Option from the type of chart [ChartOption.Week] [ChartOption.Month] [ChartOption.Year]
     * @param baselineDate Date from which the time will be based, i.e., given a date and Week option,
     *  it will reference the week that contains that date, starting from Sunday to Saturday
     */
    @NativeCoroutines
    operator fun invoke(option: ChartOption, baselineDate: LocalDate): Flow<HistoryChartData>
}