/*
 *  Copyright 2026 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.NetworkExpansionCampaign;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * JUnit tests for the Network Expansion Campaign Sling Model.
 */
@ExtendWith(AemContextExtension.class)
class NetworkExpansionCampaignImplTest {

    private final AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() throws Exception {
        ctx.addModelsForClasses(NetworkExpansionCampaignImpl.class);
        ctx.load().json("/com/adobe/aem/guides/wknd/core/models/impl/NetworkExpansionCampaignImplTest.json",
                "/content");
    }

    @Test
    void testGetHeadline() {
        final String expected = "Fiber Is Coming To Nashville";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getHeadline();

        assertEquals(expected, actual);
    }

    @Test
    void testGetDescription() {
        final String expected = "Gig-speed internet is arriving in your neighborhood with faster speeds, increased reliability, and future-ready connectivity.";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getDescription();

        assertEquals(expected, actual);
    }

    @Test
    void testGetServiceArea() {
        final String expected = "Memphis, TN";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getServiceArea();

        assertEquals(expected, actual);
    }

    @Test
    void testGetDeploymentStatus() {
        final String expected = "planning";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getDeploymentStatus();

        assertEquals(expected, actual);
    }

    @Test
    void testGetLaunchDate() {
        final String expected = "Q4 2026";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getLaunchDate();

        assertEquals(expected, actual);
    }

    @Test
    void testGetCtaText() {
        final String expected = "Check Availability";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getCtaText();

        assertEquals(expected, actual);
    }

    @Test
    void testGetCtaUrl() {
        final String expected = "/content/wknd/check-availability";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getCtaUrl();

        assertEquals(expected, actual);
    }

    @Test
    void testGetAnalyticsId() {
        final String expected = "network-expansion-nashville-q4";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getAnalyticsId();

        assertEquals(expected, actual);
    }

    @Test
    void testHasImage() {
        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertTrue(campaign.hasImage());
    }

    @Test
    void testHasImage_NoImage() {
        ctx.currentResource("/content/campaign-no-image");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertFalse(campaign.hasImage());
    }

    @Test
    void testGetImageAltText() {
        final String expected = "Fiber network infrastructure in Nashville";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getImageAltText();

        assertEquals(expected, actual);
    }

    @Test
    void testGetImageAltText_Fallback() {
        // When image alt text is not provided, should fallback to headline
        final String expected = "Fiber Is Coming To Nashville";

        ctx.currentResource("/content/campaign-no-alt-text");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getImageAltText();

        assertEquals(expected, actual);
    }

    @Test
    void testGetStatusBadgeClass_Planning() {
        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getStatusBadgeClass();

        assertEquals("badge-planning", actual);
    }

    @Test
    void testGetStatusBadgeClass_Construction() {
        ctx.currentResource("/content/campaign-construction");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getStatusBadgeClass();

        assertEquals("badge-construction", actual);
    }

    @Test
    void testGetStatusBadgeClass_Available() {
        ctx.currentResource("/content/campaign-available");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getStatusBadgeClass();

        assertEquals("badge-available", actual);
    }

    @Test
    void testIsEmpty_WithAllRequiredFields() {
        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertFalse(campaign.isEmpty());
    }

    @Test
    void testIsEmpty_MissingHeadline() {
        ctx.currentResource("/content/campaign-missing-headline");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertTrue(campaign.isEmpty());
    }

    @Test
    void testIsEmpty_MissingDeploymentStatus() {
        ctx.currentResource("/content/campaign-missing-status");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertTrue(campaign.isEmpty());
    }

    @Test
    void testIsEmpty_MissingLaunchDate() {
        ctx.currentResource("/content/campaign-missing-date");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertTrue(campaign.isEmpty());
    }

    @Test
    void testIsEmpty_MissingServiceArea() {
        ctx.currentResource("/content/campaign-missing-service-area");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertTrue(campaign.isEmpty());
    }

    @Test
    void testIsEmpty_MissingCtaText() {
        ctx.currentResource("/content/campaign-missing-cta-text");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertTrue(campaign.isEmpty());
    }

    @Test
    void testIsEmpty_MissingCtaUrl() {
        ctx.currentResource("/content/campaign-missing-cta-url");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        assertTrue(campaign.isEmpty());
    }

    @Test
    void testGetImage() {
        final String expected = "/content/dam/wknd/fiber-infrastructure.png";

        ctx.currentResource("/content/campaign");
        NetworkExpansionCampaign campaign = ctx.request().adaptTo(NetworkExpansionCampaign.class);

        String actual = campaign.getImage();

        assertEquals(expected, actual);
    }
}
