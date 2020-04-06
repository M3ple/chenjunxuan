package cn.cjx.mybatis.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParameterMappingTokenHandler implements TokenHandler {

	private List<ParameterMapping> parameterMappings = new ArrayList<>();

	// context是参数名称 #{id} #{username}

	@Override
	public String handleToken(String content) {
		parameterMappings.add(buildParameterMapping(content));
		return "?";
	}

	private ParameterMapping buildParameterMapping(String content) {
		ParameterMapping parameterMapping = new ParameterMapping(content);
		return parameterMapping;
	}

}
