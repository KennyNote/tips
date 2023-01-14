package com.learning.notebook.tips.db;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import lombok.Builder;
import lombok.Data;

/**
 * Cfgs
 *
 * @author chenguijin
 * @since 2021/6/3 20:57
 */
@Data
@Builder
public class Cfgs {
    
    private GlobalConfig gc;
    private PackageConfig pc;
    private StrategyConfig strategy;
}
