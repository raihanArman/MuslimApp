package com.raydev.shortvideo.api_infra

import com.raydev.shortvideo.ShortVideoResponse

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
interface ShortVideoService {
    fun getShortVideo(): List<ShortVideoResponse>
}
