package com.anafthdev.githubuser.data.datastore

import androidx.datastore.core.Serializer
import com.anafthdev.githubuser.ProtoUserPreference
import java.io.InputStream
import java.io.OutputStream

object UserPreferenceSerializer: Serializer<ProtoUserPreference> {

	override val defaultValue: ProtoUserPreference
		get() = ProtoUserPreference()

	override suspend fun readFrom(input: InputStream): ProtoUserPreference {
		return ProtoUserPreference.ADAPTER.decode(input)
	}

	override suspend fun writeTo(t: ProtoUserPreference, output: OutputStream) {
		ProtoUserPreference.ADAPTER.encode(output, t)
	}


}