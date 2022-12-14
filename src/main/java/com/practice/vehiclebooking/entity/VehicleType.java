package com.practice.vehiclebooking.entity;

public enum VehicleType {
    CAR {
        @Override
        public String getType(){
            return "CAR";
        }
    },

    BIKE {
        @Override
        public String getType() {
            return "BIKE";
        }
    },

    VAN {
        @Override
        public String getType() {
            return "VAN";
        }
    },

    AUTO {
        @Override
        public String getType() {
            return "AUTO";
        }
    },

    BUS {
        @Override
        public String getType() { return "BUS"; }
    };

    public abstract String getType();
}
