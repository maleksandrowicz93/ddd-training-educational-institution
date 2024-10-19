package com.github.maleksandrowicz93.edu.common.infra;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class InMemoryAbstractRepo<ID, E extends Entity<ID>> {

    private final Map<ID, E> repo = new HashMap<>();

    public void save(E e) {
        repo.put(e.id(), e);
    }

    public void saveAll(Collection<E> all) {
        all.forEach(this::save);
    }

    public void deleteBy(Predicate<E> predicate) {
        findBy(predicate)
                .map(E::id)
                .ifPresent(repo::remove);
    }

    public void deleteById(ID id) {
        repo.remove(id);
    }

    public boolean existsBy(Predicate<E> predicate) {
        return repo.values()
                   .stream()
                   .anyMatch(predicate);
    }

    public Optional<E> findBy(Predicate<E> predicate) {
        return repo.values()
                   .stream()
                   .filter(predicate)
                   .findFirst();
    }

    public Optional<E> findById(ID id) {
        return Optional.ofNullable(repo.get(id));
    }

    public List<E> findAllBy(Predicate<E> predicate) {
        return repo.values()
                   .stream()
                   .filter(predicate)
                   .toList();
    }

    public long countAll() {
        return repo.size();
    }
}
