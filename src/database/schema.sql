CREATE TABLE agencies(
  agency_id              CHAR(4),
  abbreviation           VARCHAR(20),
  name                   VARCHAR(100)

  CONSTRAINT pk_agencies PRIMARY KEY (agency_id)
);

CREATE TABLE fpds(
  piid                            VARCHAR(50), -- PIID
  agency_id                       CHAR(4), -- FUNDING_DEPARTMENT_NAME
  product                         VARCHAR(100), -- PRODUCT_OR_SERVICE_DESCRIPTION
  location_state_code             CHAR(2), -- LOCATION_STATE_CODE
  place_of_performance_country    CHAR(3), -- PLACE_OF_PERFORM_COUNTRY_CODE
  effective_date                  DATE, -- EFFECTIVE_DATE
  is_sustainable                  CHAR, -- RECOVERED_MATERIAL_CLS_DESC (Exclude "NO CLAUSES INCLUDED AND NO SUSTAINABILITY INCLUDED")
  amount                          DOUBLE PRECISION -- TOTAL_OBLIGATED_AMOUNT
);


-- ETL
CREATE TABLE fpds_bigdata
(
    piid TEXT,
    contracting_agency_id VARCHAR(150),
    ref_idv_piid VARCHAR(150),
    ref_idv_agency_id VARCHAR(150),
    modification_number VARCHAR(150),
    tocp_short_description TEXT,
    award_type_description VARCHAR(900),
    principal_naics_code VARCHAR(150),
    naics_description VARCHAR(150),
    vendor_duns_number VARCHAR(150),
    organization_type VARCHAR(150),
    description_of_requirement TEXT,
    url VARCHAR(150),
    city VARCHAR(150),
    location_state_code VARCHAR(150),
    location_state_name VARCHAR(150),
    place_of_perform_country_code VARCHAR(150),
    place_of_perform_country_name VARCHAR(150),
    place_of_perform_zip_code VARCHAR(150),
    place_congressional_district VARCHAR(150),
    effective_date VARCHAR(150),
    current_completion_date VARCHAR(150),
    ultimate_completion_date VARCHAR(150),
    last_date_to_order VARCHAR(150),
    signed_date VARCHAR(150),
    reason_for_modification_desc VARCHAR(150),
    total_obligated_amount VARCHAR(150),
    obligated_amount VARCHAR(150),
    current_contract_value VARCHAR(150),
    base_and_all_options_value VARCHAR(150),
    funding_department_id VARCHAR(150),
    funding_department_name VARCHAR(150),
    funding_agency_id VARCHAR(150),
    funding_agency_name VARCHAR(150),
    funding_office_id VARCHAR(150),
    funding_office_name VARCHAR(150),
    contracting_agency_id_1 VARCHAR(150),
    contracting_agency_name VARCHAR(150),
    contracting_office_id VARCHAR(150),
    contracting_office_name VARCHAR(150),
    ref_idv_agency_id_1 VARCHAR(150),
    multiple_or_single_award_idc VARCHAR(150),
    type_of_idc_desc VARCHAR(150),
    who_can_use TEXT,
    maximum_order_limit VARCHAR(150),
    type_of_fee_desc VARCHAR(150),
    fixed_fee_value VARCHAR(150),
    fee_paid_for_use_of_service VARCHAR(150),
    ordering_procedure TEXT,
    idv_type VARCHAR(150),
    a76_desc VARCHAR(150),
    claimant_program VARCHAR(150),
    clinger_cohen_act VARCHAR(150),
    commercial_item_acquistn_desc VARCHAR(150),
    commercial_item_test_program VARCHAR(150),
    solicitation_procedures VARCHAR(150),
    consolidated_contract VARCHAR(150),
    contingency_ops_desc VARCHAR(150),
    contract_bundling_desc VARCHAR(150),
    contract_financing_desc VARCHAR(150),
    co_bus_size_determination_desc VARCHAR(150),
    cost_acct_standards VARCHAR(150),
    cost_or_pricing_data_desc VARCHAR(150),
    country_code VARCHAR(150),
    davis_bacon_act_desc VARCHAR(150),
    vendor_division_name VARCHAR(150),
    vendor_division_number VARCHAR(150),
    evaluated_pref_description VARCHAR(150),
    extent_competed_description VARCHAR(150),
    fed_biz_opps_desc VARCHAR(150),
    foreign_funding_desc VARCHAR(150),
    gfe_gfp_desc VARCHAR(150),
    it_commercial_item_cat_desc VARCHAR(150),
    contracting_authority_desc VARCHAR(150),
    undefinitized_action_desc VARCHAR(150),
    local_area_set_aside VARCHAR(150),
    major_program_code VARCHAR(150),
    pcard_as_payment_desc VARCHAR(150),
    multiyear_contract_desc VARCHAR(150),
    national_interest_description VARCHAR(150),
    number_of_actions VARCHAR(150),
    number_of_offers_received VARCHAR(150),
    other_statutory_authority TEXT,
    perform_based_service_contract VARCHAR(150),
    place_of_manufacture_desc VARCHAR(150),
    price_eval_percent_diff VARCHAR(150),
    product_or_service_code VARCHAR(150),
    program_acronym VARCHAR(150),
    product_or_service_description VARCHAR(150),
    reason_not_comp_description VARCHAR(150),
    agency_identifier VARCHAR(150),
    recovered_material_cls_desc VARCHAR(150),
    research_description VARCHAR(150),
    sea_transportation_desc VARCHAR(150),
    service_contract_act_desc VARCHAR(150),
    sbc_demonstration_program VARCHAR(150),
    solicitation_id VARCHAR(150),
    solicitation_proc_description VARCHAR(150),
    statry_exptn_to_fair_oprtnty VARCHAR(150),
    subcontract_plan_desc VARCHAR(150),
    system_equipment_code VARCHAR(150),
    type_of_set_aside VARCHAR(150),
    use_of_epa_products_desc VARCHAR(150),
    walsh_healey_act_desc VARCHAR(150),
    transaction_number VARCHAR(150)
);


INSERT INTO agencies(agency_id, abbreviation, name)
SELECT DISTINCT FUNDING_DEPARTMENT_ID, FUNDING_DEPARTMENT_ID, FUNDING_DEPARTMENT_NAME
  FROM fpds_bigdata
 WHERE FUNDING_DEPARTMENT_ID NOT IN (SELECT agency_id FROM agencies);


INSERT INTO fpds(piid, agency_id, product, location_state_code, place_of_performance_country, effective_date, is_sustainable, amount)
SELECT PIID,
       FUNDING_DEPARTMENT_ID,
       PRODUCT_OR_SERVICE_DESCRIPTION,
       LOCATION_STATE_CODE,
       PLACE_OF_PERFORM_COUNTRY_CODE,
       TO_DATE(EFFECTIVE_DATE, 'DD-Mon-YY'),
       CASE WHEN (RECOVERED_MATERIAL_CLS_DESC = 'NO CLAUSES INCLUDED AND NO SUSTAINABILITY INCLUDED') THEN 0 ELSE 1 END,
       CAST(TOTAL_OBLIGATED_AMOUNT AS DOUBLE PRECISION)
  FROM fpds_bigdata;

