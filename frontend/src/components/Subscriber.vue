<template>
    <div>
        <String
            label="Name"
            v-model="value.name"
            :editMode="editMode"
        />
        <String
            label="Email"
            v-model="value.email"
            :editMode="editMode"
        />
        <String
            label="Subscription"
            v-model="value.subscription"
            :editMode="editMode"
        />
        <String
            label="Password"
            v-model="value.password"
            :editMode="editMode"
        />
        <Date
            label="ExpirationDate"
            v-model="value.expirationDate"
            :editMode="editMode"
        />
        <String
            label="Telecom"
            v-model="value.telecom"
            :editMode="editMode"
        />
        <String
            label="PhoneNumber"
            v-model="value.phoneNumber"
            :editMode="editMode"
        />
        <Boolean
            label="IsRecommended"
            v-model="value.isRecommended"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Subscriber',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "subscribers",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
