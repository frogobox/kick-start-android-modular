package com.frogobox.movie.mvvm.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Observer
import com.frogobox.base.service.AlarmReceiver
import com.frogobox.base.util.Constant
import com.frogobox.movie.R
import com.frogobox.movie.databinding.ActivitySettingBinding
import com.frogobox.movie.util.BaseAppActivity

class SettingActivity : BaseAppActivity<ActivitySettingBinding>() {

    private lateinit var mViewModel: SettingViewModel

    override fun setupViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        mViewModel = obtainSettingViewModel().apply {

            eventStateReleaseReminder.observe(this@SettingActivity, Observer {
                binding.switchReleaseReminder.isChecked = it
            })

            eventStateDailyReminder.observe(this@SettingActivity, Observer {
                binding.switchDailyReminder.isChecked = it
            })

        }
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupDetailActivity(getString(R.string.title_setting))
        getPref()
        setupViewElement()
    }

    private fun obtainSettingViewModel(): SettingViewModel =
        obtainViewModel(SettingViewModel::class.java)

    private fun getPref() {
        mViewModel.getPref()
    }

    private fun setupViewElement() {
        binding.ivSettingLang.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        setupSwitchListener()
    }

    private fun setupSwitchListener() {
        binding.apply {
            switchDailyReminder.setOnCheckedChangeListener { compoundButton, checked ->
                if (checked) {
                    setDailyReminder(checked)
                } else {
                    stopDailyReminder()
                }
            }

            switchReleaseReminder.setOnCheckedChangeListener { compoundButton, checked ->
                if (checked) {
                    setReleaseReminder(checked)
                } else {
                    stopReleaseReminder()
                }
            }
        }
    }

    private fun setDailyReminder(checked: Boolean) {
        mViewModel.savePrefDailyReminder(checked)
        AlarmReceiver().setRepeatingAlarm(
            this,
            resources.getString(R.string.notification_daily_reminder_title),
            resources.getString(R.string.notification_daily_reminder_text),
            Constant.Notif.TIME_DAILY_REMINDER,
            Constant.Notif.DAILY_REMINDER_NOTIFICATION_ID,
            Constant.Notif.DAILY_REMINDER_CHANNEL_ID,
            Constant.Notif.DAILY_REMINDER_CHANNEL_NAME,
            Constant.Notif.ID_REPEATING_DAILY,
            Constant.Alarm.TYPE_EXTRA_DAILY_REMINDER
        )
    }

    private fun stopDailyReminder() {
        mViewModel.deletePrefDailyReminder()
        AlarmReceiver().cancelAlarm(this, Constant.Notif.ID_REPEATING_DAILY)
    }

    private fun setReleaseReminder(checked: Boolean) {
        mViewModel.savePrefReleaseReminder(checked)
        AlarmReceiver().setRepeatingAlarm(
            this,
            getString(R.string.notification_release_today_title),
            getString(R.string.notification_release_today_text),
            Constant.Notif.TIME_DAILY_RELEASE,
            Constant.Notif.DAILY_RELEASE_NOTIFICATION_ID,
            Constant.Notif.DAILY_RELEASE_CHANNEL_ID,
            Constant.Notif.DAILY_RELEASE_CHANNEL_NAME,
            Constant.Notif.ID_REPEATING_RELEASE,
            Constant.Alarm.TYPE_EXTRA_DAILY_RELEASE
        )
    }

    private fun stopReleaseReminder() {
        mViewModel.deletePrefReleaseReminder()
        AlarmReceiver().cancelAlarm(this, Constant.Notif.ID_REPEATING_RELEASE)
    }

}