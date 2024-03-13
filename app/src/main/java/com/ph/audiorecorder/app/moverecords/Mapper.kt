package com.ph.audiorecorder.app.moverecords

import com.ph.audiorecorder.app.formatRecordInformation
import com.ph.audiorecorder.app.settings.SettingsMapper
import com.ph.audiorecorder.data.database.Record

fun recordToMoveRecordsItem(settingsMapper: SettingsMapper, item: Record): MoveRecordsItem {
	return MoveRecordsItem(
		item.id,
		item.name,
		formatRecordInformation(settingsMapper, item.format, item.sampleRate, item.size)
	)
}

fun recordsToMoveRecordsItems(settingsMapper: SettingsMapper, items: List<Record>): List<MoveRecordsItem> {
	return items.map { recordToMoveRecordsItem(settingsMapper, it) }
}
