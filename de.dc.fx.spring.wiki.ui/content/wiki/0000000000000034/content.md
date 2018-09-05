Link: https://stackoverflow.com/questions/51268281/spring-data-jpa-query-method-to-find-all-objects-with-empty-list-property


!!! note "Source Code"
@Repository
public interface JobRepository extends CrudRepository<Job, Integer> {
    Page<Job> findByJobShipmentsIsNull(Pageable pageable);
}

