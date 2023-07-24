/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.provider.proxies;

import org.gradle.api.provider.MapProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of Map, that is used for Property upgrades
 */
@NotThreadSafe
public class MapPropertyBackedMap<K, V> extends AbstractMap<K, V> {

    private final MapProperty<K, V> delegate;

    public MapPropertyBackedMap(MapProperty<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V get(Object key) {
        return delegate.get().get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.get().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.get().containsValue(value);
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        // TODO: Should we return a live view of the map?
        return delegate.get().entrySet();
    }

    @Override
    public V put(K key, V value) {
        Map<K, V> map = new LinkedHashMap<>(delegate.get());
        V oldValue = map.put(key, value);
        delegate.set(map);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
    }

    @Override
    public V remove(Object key) {
        Map<K, V> map = new LinkedHashMap<>(delegate.get());
        V oldValue = map.remove(key);
        delegate.set(map);
        return oldValue;
    }

    @Override
    public void clear() {
        delegate.empty();
    }
}
