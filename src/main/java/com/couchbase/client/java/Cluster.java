/*
 * Copyright (c) 2016 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.couchbase.client.java;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.auth.Authenticator;
import com.couchbase.client.java.auth.CredentialContext;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.error.AuthenticatorException;
import com.couchbase.client.java.transcoder.Transcoder;

/**
 * Represents a Couchbase Server {@link Cluster}.
 *
 * A {@link Cluster} is able to open many {@link Bucket}s while sharing the underlying resources very
 * efficiently. In addition, the {@link ClusterManager} is available to perform cluster-wide operations.
 *
 * @author Michael Nitschinger
 * @since 2.0
 */
@InterfaceStability.Committed
@InterfaceAudience.Public
public interface Cluster {

    /**
     * Opens the default bucket with an empty password with the default connect timeout.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *
     * @return the opened bucket if successful.
     */
    Bucket openBucket();

    /**
     * Opens the default bucket with an empty password with a custom timeout.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *
     * @param timeout the custom timeout.
     * @param timeUnit the time unit for the custom timeout.
     * @return the opened bucket if successful.
     */
    Bucket openBucket(long timeout, TimeUnit timeUnit);

    /**
     * Opens the bucket with the given name, using the default timeout and the password from the {@link Authenticator}
     * that was last {@link #authenticate(Authenticator) set}
     * (in the {@link CredentialContext#BUCKET_KV BUCKET_KV context}).
     *
     * If no credential can be found for the bucket in the authenticator, the old behavior of defaulting to an empty
     * password is used.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *  - {@link AuthenticatorException}: If more than one credentials was returned by the Authenticator for this bucket.

     *
     * @return the opened bucket if successful.
     */
    Bucket openBucket(String name);

    /**
     * Opens the bucket with the given name, using a custom timeout and the password from the {@link Authenticator}
     * that was last {@link #authenticate(Authenticator) set}
     * (in the {@link CredentialContext#BUCKET_KV BUCKET_KV context}).
     *
     * If no credential can be found for the bucket in the authenticator, the old behavior of defaulting to an empty
     * password is used.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *  - {@link AuthenticatorException}: If more than one credentials was returned by the Authenticator for this bucket.
     *
     * @param timeout the custom timeout.
     * @param timeUnit the time unit for the custom timeout.
     * @return the opened bucket if successful.
     */
    Bucket openBucket(String name, long timeout, TimeUnit timeUnit);

    /**
     * Opens a bucket identified by its name and password with the default connect timeout.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *
     * @return the opened bucket if successful.
     */
    Bucket openBucket(String name, String password);

    /**
     * Opens a bucket identified by its name and password with a custom timeout.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *
     * @param timeout the custom timeout.
     * @param timeUnit the time unit for the custom timeout.
     * @return the opened bucket if successful.
     */
    Bucket openBucket(String name, String password, long timeout, TimeUnit timeUnit);

    /**
     * Opens a bucket identified by its name and password with custom transcoders and with the default connect timeout.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *
     * @return the opened bucket if successful.
     */
    Bucket openBucket(String name, String password, List<Transcoder<? extends Document, ?>> transcoders);

    /**
     * Opens a bucket identified by its name and password with custom transcoders and with a custom timeout.
     *
     * This method throws:
     *
     *  - java.util.concurrent.TimeoutException: If the timeout is exceeded.
     *  - com.couchbase.client.core.CouchbaseException: If the bucket could not be opened (see logs and nested stack
     *    trace for more details why it failed).
     *  - com.couchbase.client.core.BackpressureException: If the incoming request rate is too high to be processed.
     *
     * @param timeout the custom timeout.
     * @param timeUnit the time unit for the custom timeout.
     * @return the opened bucket if successful.
     */
    Bucket openBucket(String name, String password, List<Transcoder<? extends Document, ?>> transcoders,
        long timeout, TimeUnit timeUnit);


    /**
     * Provides access to the {@link ClusterManager} to perform cluster-wide operations.
     *
     * Note that the credentials provided here are different from bucket-level credentials. As a rule of thumb, the
     * "Administrator" credentials need to be passed in here or any credentials with enough permissions to perform
     * the underlying operations. **Bucket level credentials will not work.**
     *
     * @param username the username to perform cluster-wide operations.
     * @param password the password associated with the username.
     * @return the {@link ClusterManager} if successful.
     */
    ClusterManager clusterManager(String username, String password);

    /**
     * Provides access to the {@link ClusterManager} to perform cluster-wide operations, using the credentials set
     * through the configured {@link Authenticator} (see {@link #authenticate(Authenticator)}), for the
     * {@link CredentialContext#CLUSTER_MANAGEMENT} context.
     *
     * @return the {@link ClusterManager} if successful.
     * @throws AuthenticatorException if no {@link Authenticator} is set or it doesn't contain a cluster management
     * credential.
     */
    ClusterManager clusterManager();

    /**
     * Disconnects form all open buckets and shuts down the {@link CouchbaseEnvironment} if it is the exclusive owner
     * with the default disconnect timeout.
     *
     * @return true once done and everything succeeded, false otherwise.
     */
    Boolean disconnect();

    /**
     * Disconnects form all open buckets and shuts down the {@link CouchbaseEnvironment} if it is the exclusive owner
     * with a custom timeout.
     *
     * @return true once done and everything succeeded, false otherwise.
     */
    Boolean disconnect(long timeout, TimeUnit timeUnit);

    /**
     * Returns the underlying "core-io" library through its {@link ClusterFacade}.
     *
     * Handle with care, with great power comes great responsibility. All additional checks which are normally performed
     * by this library are skipped.
     *
     * @return the underlying {@link ClusterFacade} from the "core-io" package.
     */
    ClusterFacade core();

    /**
     * Sets the {@link Authenticator} to use when credentials are needed for an operation
     * but no explicit credentials are provided.
     *
     * Note that setting a new Authenticator will not be propagated to any {@link Bucket} that
     * has been opened with the previous Authenticator, as the instance is passed to the Bucket
     * for its own use.
     *
     * @param auth the new {@link Authenticator} to use.
     * @return this Cluster instance for chaining.
     */
    Cluster authenticate(Authenticator auth);
}
