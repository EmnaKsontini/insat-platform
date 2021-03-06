package insat.company.platform.service.impl;

import insat.company.platform.domain.User;
import insat.company.platform.service.IntegrationService;
import insat.company.platform.domain.Integration;
import insat.company.platform.repository.IntegrationRepository;
import insat.company.platform.repository.search.IntegrationSearchRepository;
import insat.company.platform.service.dto.IntegrationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Integration.
 */
@Service
@Transactional
public class IntegrationServiceImpl implements IntegrationService {

    private final Logger log = LoggerFactory.getLogger(IntegrationServiceImpl.class);

    private final IntegrationRepository integrationRepository;

    private final IntegrationSearchRepository integrationSearchRepository;

    public IntegrationServiceImpl(IntegrationRepository integrationRepository, IntegrationSearchRepository integrationSearchRepository) {
        this.integrationRepository = integrationRepository;
        this.integrationSearchRepository = integrationSearchRepository;
    }

    /**
     * Save a integration.
     *
     * @param integration the entity to save
     * @return the persisted entity
     */
    @Override
    public Integration save(Integration integration) {
        log.debug("Request to save Integration : {}", integration);
        Integration result = integrationRepository.save(integration);
        integrationSearchRepository.save(result);
        return result;
    }
    @Override
    public Integration save(IntegrationDTO integrationDTO , User user ) {
        log.debug("Request to save Integration : {}", integrationDTO);
        Integration result = integrationRepository.save(new Integration(integrationDTO, user));
        integrationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the integrations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Integration> findAll(Pageable pageable) {
        log.debug("Request to get all Integrations");
        return integrationRepository.findAll(pageable);
    }


    /**
     * Get one integration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Integration> findOne(Long id) {
        log.debug("Request to get Integration : {}", id);
        return integrationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integration findOneByTeamId(String teamId) {
        log.debug("Request to get Integration by teamId : {}", teamId);
        return integrationRepository.findOneByTeamId(teamId);
    }

    /**
     * Delete the integration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Integration : {}", id);
        integrationRepository.deleteById(id);
        integrationSearchRepository.deleteById(id);
    }

    /**
     * Search for the integration corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Integration> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Integrations for query {}", query);
        return integrationSearchRepository.search(queryStringQuery(query), pageable);
    }
}
