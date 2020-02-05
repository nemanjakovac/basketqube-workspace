package com.logiqube.basketqube.dataimport.repository;

public interface TeamRepositoryCustom {
	
//    private ProjectionOperation getProjectData() {
//        return project("orderId", "name", "dateCreated", "status")
//                .and("deliveryMethod.deliveryDate").as("dueDate")
//                .and(AccumulatorOperators.Sum.sumOf("items.selectedQuantity")).as("selectedQuantity");
//    }
//	
//	@Override
//	public List<TeamDto> findAllOrdersProjected() {
//		return mongoTemplate.aggregate(
//				Aggregation.newAggregation(
//						getProjectData()
//						),
//				Team.class, TeamDto.class).getMappedResults();
//	}

}
