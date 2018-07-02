/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.jackrabbit.oak.segment.osgi;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SegmentNodeStoreFactoryTest {

    @Test
    public void testComponentDescriptor() throws Exception {
        ComponentDescriptor cd = ComponentDescriptor.open(getClass().getResourceAsStream("/OSGI-INF/org.apache.jackrabbit.oak.segment.SegmentNodeStoreFactory.xml"));
        assertTrue(cd.hasName("org.apache.jackrabbit.oak.segment.SegmentNodeStoreFactory"));
        assertTrue(cd.hasRequireConfigurationPolicy());
        assertTrue(cd.hasActivateMethod("activate"));
        assertTrue(cd.hasDeactivateMethod("deactivate"));
        assertTrue(cd.hasProperty("role").check());
        assertTrue(cd.hasProperty("customBlobStore")
            .withBooleanType()
            .withValue("false")
            .check());
        assertTrue(cd.hasProperty("customSegmentStore")
            .withBooleanType()
            .withValue("false")
            .check());
        assertTrue(cd.hasProperty("registerDescriptors")
            .withBooleanType()
            .withValue("false")
            .check());
        assertTrue(cd.hasReference("blobStore")
            .withInterface("org.apache.jackrabbit.oak.spi.blob.BlobStore")
            .withOptionalUnaryCardinality()
            .withStaticPolicy()
            .withGreedyPolicyOption()
            .withTarget("(&(!(split.blobstore=old))(!(split.blobstore=new)))")
            .withBind("bindBlobStore")
            .withUnbind("unbindBlobStore")
            .check());
        assertTrue(cd.hasReference("segmentStore")
            .withInterface("org.apache.jackrabbit.oak.segment.spi.persistence.SegmentNodeStorePersistence")
            .withOptionalUnaryCardinality()
            .withStaticPolicy()
            .withGreedyPolicyOption()
            .withBind("bindSegmentStore")
            .withUnbind("unbindSegmentStore")
            .check());
        assertTrue(cd.hasReference("statisticsProvider")
            .withInterface("org.apache.jackrabbit.oak.stats.StatisticsProvider")
            .withMandatoryUnaryCardinality()
            .withStaticPolicy()
            .withBind("bindStatisticsProvider")
            .withUnbind("unbindStatisticsProvider")
            .check());

    }
}